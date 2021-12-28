import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './user.reducer';
import { IUser } from 'app/shared/model/user.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { ActorType } from 'app/shared/model/enumerations/actor-type.model';

export const UserUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const userEntity = useAppSelector(state => state.user.entity);
  const loading = useAppSelector(state => state.user.loading);
  const updating = useAppSelector(state => state.user.updating);
  const updateSuccess = useAppSelector(state => state.user.updateSuccess);
  const actorTypeValues = Object.keys(ActorType);
  const handleClose = () => {
    props.history.push('/user');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...userEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          type: 'Buyer',
          ...userEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="mulyaaApp.user.home.createOrEditLabel" data-cy="UserCreateUpdateHeading">
            <Translate contentKey="mulyaaApp.user.home.createOrEditLabel">Create or edit a User</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="user-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('mulyaaApp.user.email')}
                id="user-email"
                name="email"
                data-cy="email"
                type="text"
                validate={{
                  pattern: {
                    value: /^[^@\s]+@[^@\s]+\.[^@\s]+$/,
                    message: translate('entity.validation.pattern', { pattern: '^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$' }),
                  },
                }}
              />
              <ValidatedField
                label={translate('mulyaaApp.user.phone')}
                id="user-phone"
                name="phone"
                data-cy="phone"
                type="text"
                validate={{
                  min: { value: 1000000000, message: translate('entity.validation.min', { min: 1000000000 }) },
                  max: { value: 9999999999, message: translate('entity.validation.max', { max: 9999999999 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('mulyaaApp.user.isEmailVerified')}
                id="user-isEmailVerified"
                name="isEmailVerified"
                data-cy="isEmailVerified"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('mulyaaApp.user.isPhoneVerified')}
                id="user-isPhoneVerified"
                name="isPhoneVerified"
                data-cy="isPhoneVerified"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('mulyaaApp.user.isActive')}
                id="user-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('mulyaaApp.user.password')}
                id="user-password"
                name="password"
                data-cy="password"
                type="text"
              />
              <ValidatedField label={translate('mulyaaApp.user.type')} id="user-type" name="type" data-cy="type" type="select">
                {actorTypeValues.map(actorType => (
                  <option value={actorType} key={actorType}>
                    {translate('mulyaaApp.ActorType.' + actorType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('mulyaaApp.user.createdOn')}
                id="user-createdOn"
                name="createdOn"
                data-cy="createdOn"
                type="date"
              />
              <ValidatedField
                label={translate('mulyaaApp.user.createdBy')}
                id="user-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('mulyaaApp.user.updatedOn')}
                id="user-updatedOn"
                name="updatedOn"
                data-cy="updatedOn"
                type="date"
              />
              <ValidatedField
                label={translate('mulyaaApp.user.updatedBy')}
                id="user-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/user" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default UserUpdate;
