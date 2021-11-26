import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './requirement.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const RequirementDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const requirementEntity = useAppSelector(state => state.requirement.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="requirementDetailsHeading">
          <Translate contentKey="mullyaApp.requirement.detail.title">Requirement</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{requirementEntity.id}</dd>
          <dt>
            <span id="category">
              <Translate contentKey="mullyaApp.requirement.category">Category</Translate>
            </span>
          </dt>
          <dd>{requirementEntity.category}</dd>
          <dt>
            <span id="variant">
              <Translate contentKey="mullyaApp.requirement.variant">Variant</Translate>
            </span>
          </dt>
          <dd>{requirementEntity.variant}</dd>
          <dt>
            <span id="subVariant">
              <Translate contentKey="mullyaApp.requirement.subVariant">Sub Variant</Translate>
            </span>
          </dt>
          <dd>{requirementEntity.subVariant}</dd>
          <dt>
            <span id="minPrice">
              <Translate contentKey="mullyaApp.requirement.minPrice">Min Price</Translate>
            </span>
          </dt>
          <dd>{requirementEntity.minPrice}</dd>
          <dt>
            <span id="maxPrice">
              <Translate contentKey="mullyaApp.requirement.maxPrice">Max Price</Translate>
            </span>
          </dt>
          <dd>{requirementEntity.maxPrice}</dd>
          <dt>
            <span id="quantityKg">
              <Translate contentKey="mullyaApp.requirement.quantityKg">Quantity Kg</Translate>
            </span>
          </dt>
          <dd>{requirementEntity.quantityKg}</dd>
          <dt>
            <span id="neededBy">
              <Translate contentKey="mullyaApp.requirement.neededBy">Needed By</Translate>
            </span>
          </dt>
          <dd>{requirementEntity.neededBy}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="mullyaApp.requirement.description">Description</Translate>
            </span>
          </dt>
          <dd>{requirementEntity.description}</dd>
          <dt>
            <span id="createdOn">
              <Translate contentKey="mullyaApp.requirement.createdOn">Created On</Translate>
            </span>
          </dt>
          <dd>
            {requirementEntity.createdOn ? (
              <TextFormat value={requirementEntity.createdOn} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="mullyaApp.requirement.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{requirementEntity.createdBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="mullyaApp.requirement.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>
            {requirementEntity.updatedOn ? (
              <TextFormat value={requirementEntity.updatedOn} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="mullyaApp.requirement.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{requirementEntity.updatedBy}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="mullyaApp.requirement.status">Status</Translate>
            </span>
          </dt>
          <dd>{requirementEntity.status}</dd>
          <dt>
            <span id="paymentStatus">
              <Translate contentKey="mullyaApp.requirement.paymentStatus">Payment Status</Translate>
            </span>
          </dt>
          <dd>{requirementEntity.paymentStatus}</dd>
          <dt>
            <span id="deliveryStatus">
              <Translate contentKey="mullyaApp.requirement.deliveryStatus">Delivery Status</Translate>
            </span>
          </dt>
          <dd>{requirementEntity.deliveryStatus}</dd>
          <dt>
            <Translate contentKey="mullyaApp.requirement.address">Address</Translate>
          </dt>
          <dd>{requirementEntity.address ? requirementEntity.address.id : ''}</dd>
          <dt>
            <Translate contentKey="mullyaApp.requirement.actor">Actor</Translate>
          </dt>
          <dd>{requirementEntity.actor ? requirementEntity.actor.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/requirement" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/requirement/${requirementEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RequirementDetail;
