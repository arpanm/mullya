import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IHub } from 'app/shared/model/hub.model';
import { getEntities as getHubs } from 'app/entities/hub/hub.reducer';
import { IUser } from 'app/shared/model/user.model';
import { getEntities as getUsers } from 'app/entities/user/user.reducer';
import { getEntity, updateEntity, createEntity, reset } from './address.reducer';
import { IAddress } from 'app/shared/model/address.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const AddressUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const hubs = useAppSelector(state => state.hub.entities);
  const users = useAppSelector(state => state.user.entities);
  const addressEntity = useAppSelector(state => state.address.entity);
  const loading = useAppSelector(state => state.address.loading);
  const updating = useAppSelector(state => state.address.updating);
  const updateSuccess = useAppSelector(state => state.address.updateSuccess);
  const handleClose = () => {
    props.history.push('/address');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getHubs({}));
    dispatch(getUsers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...addressEntity,
      ...values,
      hub: hubs.find(it => it.id.toString() === values.hub.toString()),
      user: users.find(it => it.id.toString() === values.user.toString()),
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
          ...addressEntity,
          hub: addressEntity?.hub?.id,
          user: addressEntity?.user?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="mulyaaApp.address.home.createOrEditLabel" data-cy="AddressCreateUpdateHeading">
            <Translate contentKey="mulyaaApp.address.home.createOrEditLabel">Create or edit a Address</Translate>
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
                  id="address-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('mulyaaApp.address.streetAddress')}
                id="address-streetAddress"
                name="streetAddress"
                data-cy="streetAddress"
                type="text"
              />
              <ValidatedField
                label={translate('mulyaaApp.address.postalCode')}
                id="address-postalCode"
                name="postalCode"
                data-cy="postalCode"
                type="text"
              />
              <ValidatedField label={translate('mulyaaApp.address.city')} id="address-city" name="city" data-cy="city" type="text" />
              <ValidatedField
                label={translate('mulyaaApp.address.stateProvince')}
                id="address-stateProvince"
                name="stateProvince"
                data-cy="stateProvince"
                type="text"
              />
              <ValidatedField
                label={translate('mulyaaApp.address.country')}
                id="address-country"
                name="country"
                data-cy="country"
                type="text"
              />
              <ValidatedField
                label={translate('mulyaaApp.address.createdBy')}
                id="address-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField label={translate('mulyaaApp.address.lat')} id="address-lat" name="lat" data-cy="lat" type="text" />
              <ValidatedField label={translate('mulyaaApp.address.lon')} id="address-lon" name="lon" data-cy="lon" type="text" />
              <ValidatedField
                label={translate('mulyaaApp.address.mapLocation')}
                id="address-mapLocation"
                name="mapLocation"
                data-cy="mapLocation"
                type="text"
              />
              <ValidatedField
                label={translate('mulyaaApp.address.createdOn')}
                id="address-createdOn"
                name="createdOn"
                data-cy="createdOn"
                type="date"
              />
              <ValidatedField
                label={translate('mulyaaApp.address.updatedOn')}
                id="address-updatedOn"
                name="updatedOn"
                data-cy="updatedOn"
                type="date"
              />
              <ValidatedField
                label={translate('mulyaaApp.address.updatedBy')}
                id="address-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField id="address-hub" name="hub" data-cy="hub" label={translate('mulyaaApp.address.hub')} type="select">
                <option value="" key="0" />
                {hubs
                  ? hubs.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="address-user" name="user" data-cy="user" label={translate('mulyaaApp.address.user')} type="select">
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/address" replace color="info">
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

export default AddressUpdate;
