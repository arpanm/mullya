import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './actor.reducer';
import { IActor } from 'app/shared/model/actor.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { ActorType } from 'app/shared/model/enumerations/actor-type.model';

export const ActorUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const actorEntity = useAppSelector(state => state.actor.entity);
  const loading = useAppSelector(state => state.actor.loading);
  const updating = useAppSelector(state => state.actor.updating);
  const updateSuccess = useAppSelector(state => state.actor.updateSuccess);
  const actorTypeValues = Object.keys(ActorType);
  const handleClose = () => {
    props.history.push('/actor');
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
      ...actorEntity,
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
          ...actorEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="mullyaApp.actor.home.createOrEditLabel" data-cy="ActorCreateUpdateHeading">
            <Translate contentKey="mullyaApp.actor.home.createOrEditLabel">Create or edit a Actor</Translate>
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
                  id="actor-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('mullyaApp.actor.email')}
                id="actor-email"
                name="email"
                data-cy="email"
                type="text"
                validate={{
                  pattern: { value: /^(.+)@(.+)$/, message: translate('entity.validation.pattern', { pattern: '^(.+)@(.+)$' }) },
                }}
              />
              <ValidatedField
                label={translate('mullyaApp.actor.phone')}
                id="actor-phone"
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
                label={translate('mullyaApp.actor.isEmailVerified')}
                id="actor-isEmailVerified"
                name="isEmailVerified"
                data-cy="isEmailVerified"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('mullyaApp.actor.isPhoneVerified')}
                id="actor-isPhoneVerified"
                name="isPhoneVerified"
                data-cy="isPhoneVerified"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('mullyaApp.actor.isActive')}
                id="actor-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('mullyaApp.actor.password')}
                id="actor-password"
                name="password"
                data-cy="password"
                type="text"
              />
              <ValidatedField label={translate('mullyaApp.actor.type')} id="actor-type" name="type" data-cy="type" type="select">
                {actorTypeValues.map(actorType => (
                  <option value={actorType} key={actorType}>
                    {translate('mullyaApp.ActorType.' + actorType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('mullyaApp.actor.createdOn')}
                id="actor-createdOn"
                name="createdOn"
                data-cy="createdOn"
                type="date"
              />
              <ValidatedField
                label={translate('mullyaApp.actor.createdBy')}
                id="actor-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('mullyaApp.actor.updatedOn')}
                id="actor-updatedOn"
                name="updatedOn"
                data-cy="updatedOn"
                type="date"
              />
              <ValidatedField
                label={translate('mullyaApp.actor.updatedBy')}
                id="actor-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/actor" replace color="info">
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

export default ActorUpdate;
